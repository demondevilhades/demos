package test.nlp.segment;

import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import test.nlp.Constant;
import test.nlp.common.Attribute;
import test.nlp.common.Vertex;
import test.nlp.dict.CoreDictionary;
import test.nlp.dict.nr.NR;
import test.nlp.dict.nr.NRPattern;
import test.nlp.dict.nr.PersonDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.dict.pos.POSAttribute;
import test.nlp.trie.IHit;
import test.nlp.util.Viterbi;

/**
 * 人名识别类
 */
public class PersonRecognition {

    /**
     * 人名识别
     * 
     * @param optimizeVertexes
     *            初次分词消歧结果
     * @param graph
     *            初分分词图表
     * @param optimizeGraph
     *            根据初次分词结果创建的分词图表
     */
    public static void personRecognize(LinkedList<Vertex> optimizeVertexes, Graph graph, Graph optimizeGraph) {
        LinkedList<VertexAttribute<NR>> vertexAttributes = roleTag(optimizeVertexes);
        String roleText = viterbi(vertexAttributes, optimizeVertexes);
        parsePattern(roleText, optimizeVertexes, graph, optimizeGraph);
    }

    /**
     * 模式识别
     * 
     * @param nrList
     * @param optimizeVertexes
     * @param graph
     * @param optimizeGraph
     */
    private static void parsePattern(String roleText, LinkedList<Vertex> optimizeVertexes, final Graph graph,
            final Graph optimizeGraph) {
        final Vertex[] vertexes = optimizeVertexes.toArray(new Vertex[optimizeVertexes.size()]);
        PersonDictionary.getInstance().getPatternDictionary().parse(roleText.toCharArray(), new IHit<NRPattern>() {

            @Override
            public void hit(int begin, int end, NRPattern value) {
                StringBuilder personNameBuilder = new StringBuilder();
                for (int i = begin; i <= end; i++) {
                    personNameBuilder.append(vertexes[i].getWord());
                }
                String personName = personNameBuilder.toString();
                if (!isBadCase(personName)) {
                    optimizeGraph.addVertex(
                            new Vertex(vertexes[begin].getIndex(), personName, Constant.UNKNOWN_PERSON, CoreDictionary
                                    .getInstance().getUnknownPOSAttr(Constant.UNKNOWN_PERSON, new POS[] { POS.nr })),
                            graph);
                }
            }

            @Override
            public void notHit(int begin) {

            }
        });
    }

    /**
     * 判断是否是bad case
     * 
     * @param personName
     * @return
     */
    private static boolean isBadCase(String personName) {
        if (StringUtils.containsAny(personName, '的', '在')) {
            return true;
        }
        return false;
    }

    /**
     * 人名角色标注
     * 
     * @param optimizeVertexes
     * @return
     */
    private static LinkedList<VertexAttribute<NR>> roleTag(LinkedList<Vertex> optimizeVertexes) {
        LinkedList<VertexAttribute<NR>> res = Lists.newLinkedList();
        for (Vertex vertex : optimizeVertexes) {
            Attribute<NR> attribute = PersonDictionary.getInstance().getNRAttr(vertex.getText());
            if (attribute == null) {
                if (vertex.length() == 2) {
                    if (POS.nr == vertex.getPos() || POS.nr2 == vertex.getPos()) {
                        attribute = new Attribute<NR>(NR.W, NR.X, NR.Z);
                    } else {
                        attribute = new Attribute<NR>(NR.A, vertex.getTotalFreq());
                    }
                }
                attribute = new Attribute<NR>(NR.A, vertex.getTotalFreq());
            }
            res.add(new VertexAttribute<NR>(attribute));
        }
        res.add(new VertexAttribute<NR>(new Attribute<NR>(NR.A)));
        return res;
    }

    private static String viterbi(LinkedList<VertexAttribute<NR>> vertexAttributes, LinkedList<Vertex> optimizeVertexes) {
        LinkedList<NR> roles = Viterbi.compute(vertexAttributes, new VertexAttribute<NR>(new Attribute<NR>(NR.S)),
                PersonDictionary.getInstance().getTransformMatrixDictionary());

        StringBuilder roleText = new StringBuilder();
        // 拆分U,V
        ListIterator<Vertex> vertexIterator = optimizeVertexes.listIterator();
        NR prevRole = null;
        for (NR role : roles) {
            Vertex vertex = vertexIterator.next();
            switch (role) {
            case U:
                splitU(roleText, vertexIterator, vertex);
                prevRole = NR.B;
                break;
            case V:
                splitV(roleText, vertexIterator, vertex, prevRole);
                prevRole = NR.L;
            default:
                roleText.append(role.toString());
                prevRole = role;
                break;
            }
        }
        return roleText.toString();
    }

    /**
     * 拆分V，拆分成DL或EL
     * 
     * @param roleText
     * @param vertexIterator
     * @param vertex
     * @param prevRole
     */
    private static void splitV(StringBuilder roleText, ListIterator<Vertex> vertexIterator, Vertex vertex, NR prevRole) {

        vertexIterator.remove();
        String first = vertex.getWord().substring(0, 1);
        String second = vertex.getWord().substring(1);
        vertexIterator.add(new Vertex(vertex.getIndex(), first, new POSAttribute(0)));
        vertexIterator.add(new Vertex(vertex.getIndex() + first.length(), second, new POSAttribute(0)));

        if (NR.C == prevRole) {
            roleText.append(NR.D.toString());
        } else {
            roleText.append(NR.E.toString());
        }
        roleText.append(NR.L.toString());

    }

    /**
     * 拆分U，拆分成KB
     * 
     * @param roleText
     * @param vertexIterator
     * @param vertex
     * 
     */
    private static void splitU(StringBuilder roleText, ListIterator<Vertex> vertexIterator, Vertex vertex) {

        vertexIterator.remove();

        String first = vertex.getWord().substring(0, vertex.length() - 1);
        String second = vertex.getWord().substring(first.length());

        vertexIterator.add(new Vertex(vertex.getIndex(), first, new POSAttribute(0)));
        vertexIterator.add(new Vertex(vertex.getIndex() + first.length(), second, new POSAttribute(0)));

        roleText.append(NR.K);
        roleText.append(NR.B);
    }

}
