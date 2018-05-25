package test.nlp.highlight;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

public class HighlighterTest {

    public void testCJK() {
        Highlighter highlighter = new Highlighter(Sets.newHashSet(new String[] { "中国" }));
        Assert.assertEquals("<em>中国</em>", highlighter.highlight("中国", -1));
        Assert.assertEquals("测试高亮<em>中国</em>", highlighter.highlight("测试高亮中国", 10));
        Assert.assertEquals("测试高亮 <em>中国</em>", highlighter.highlight("测试高亮 中国", 10));
        Assert.assertEquals("测试高亮abc <em>中国</em>", highlighter.highlight("测试高亮abc 中国", 10));
        Assert.assertEquals("测试高亮 <em>中国</em>人", highlighter.highlight("测试高亮 中国人", 10));
    }

    public void testLetter() {
        Highlighter highlighter = new Highlighter(Sets.newHashSet(new String[] { "iphon", "phone" }));
        Assert.assertEquals("<em>Iphone</em>", highlighter.highlight("Iphone", 10));
        Assert.assertEquals("测试高亮<em>iphone</em>", highlighter.highlight("测试高亮iphone", 10));
        Assert.assertEquals("测试高亮<em>iphones</em>", highlighter.highlight("测试高亮iphones", 20));
        Assert.assertEquals("测试高亮iphone", highlighter.highlight("测试高亮iphoneadf", 10));
    }

    public void testNumber() {
        Highlighter highlighter = new Highlighter(Sets.newHashSet(new String[] { "23", "234.5" }));
        Assert.assertEquals("<em>23</em>", highlighter.highlight("23", 10));
        Assert.assertEquals("测试高亮<em>23</em>", highlighter.highlight("测试高亮23", 10));
        Assert.assertEquals("测试高亮234", highlighter.highlight("测试高亮234", 10));
        Assert.assertEquals("测试高亮<em>234.5</em>", highlighter.highlight("测试高亮234.5", 10));
        Assert.assertEquals("测试高亮<em>23</em>a", highlighter.highlight("测试高亮23a", 10));
    }

    @Test
    public void testAll() {
        Highlighter highlighter = new Highlighter(Sets.newHashSet(new String[] { "户口" }));
        System.out.println(highlighter.highlight(" 办理《 注销户口通知》", 100));
    }
}
