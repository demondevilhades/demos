package test.nlp.segment;

import java.util.LinkedList;

import com.google.common.collect.Lists;
import test.nlp.Constant;
import test.nlp.common.Attribute;
import test.nlp.common.Vertex;
import test.nlp.dict.CoreDictionary;
import test.nlp.dict.ns.NS;
import test.nlp.dict.ns.NSPattern;
import test.nlp.dict.ns.PlaceDictionary;
import test.nlp.dict.pos.POS;
import test.nlp.trie.IHit;
import test.nlp.util.StringUtils;
import test.nlp.util.Viterbi;

/**
 * 地名识别类
 */
public class PlaceRecognition {

    /**
     * 地名识别
     * 
     * @param optimizeVertexes
     *            初次分词消歧结果
     * @param graph
     *            初分分词图表
     * @param optimizeGraph
     *            根据初次分词结果创建的分词图表
     */
    public static void placeRecognize(LinkedList<Vertex> optimizeVertexes, Graph graph, Graph optimizeGraph) {
        LinkedList<VertexAttribute<NS>> nsRoleList = roleTag(optimizeVertexes);
        LinkedList<NS> roleTags = Viterbi.compute(nsRoleList, new VertexAttribute<NS>(new Attribute<NS>(NS.S)),
                PlaceDictionary.getInstance().getNsTransformDictionary());
        parsePattern(roleTags, optimizeVertexes, graph, optimizeGraph);
    }

    /**
     * 角色标注
     * 
     * @param optimizeVertexes
     */
    private static LinkedList<VertexAttribute<NS>> roleTag(LinkedList<Vertex> optimizeVertexes) {
        LinkedList<VertexAttribute<NS>> res = Lists.newLinkedList();
        for (Vertex vertex : optimizeVertexes) {
            Attribute<NS> attr = PlaceDictionary.getInstance().getNSAttr(vertex.getText());
            if (attr == null) {
                attr = new Attribute<NS>(NS.A, vertex.getTotalFreq());
            }
            res.add(new VertexAttribute<NS>(attr));
        }
        res.add(new VertexAttribute<NS>(new Attribute<NS>(NS.A)));
        return res;
    }

    /**
     * 模式识别
     * 
     * @param roleTags
     * @param optimizeVertexes
     * @param graph
     * @param optimizeGraph
     */
    private static void parsePattern(LinkedList<NS> roleTags, LinkedList<Vertex> optimizeVertexes, final Graph graph,
            final Graph optimizeGraph) {
        String roleText = StringUtils.EMPTY_JOINER.join(roleTags);
        final Vertex[] vertexes = new Vertex[optimizeVertexes.size()];
        optimizeVertexes.toArray(vertexes);

        PlaceDictionary.getInstance().getPattern().parse(roleText.toCharArray(), new IHit<NSPattern>() {

            @Override
            public void hit(int begin, int end, NSPattern value) {
                StringBuilder placeNameBuilder = new StringBuilder();
                for (int i = begin; i <= end; i++) {
                    placeNameBuilder.append(vertexes[i].getWord());
                }
                optimizeGraph.addVertex(
                        new Vertex(vertexes[begin].getIndex(), placeNameBuilder.toString(), Constant.UNKNOWN_SPACE,
                                CoreDictionary.getInstance().get(Constant.UNKNOWN_SPACE).clone(new POS[] { POS.ns })),
                        graph);
            }

            @Override
            public void notHit(int begin) {

            }
        });

    }
}
