package hades.itext.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor
public class RichTextRenderListener implements RenderListener {

    private final List<Integer> mcidList = new ArrayList<>();

    private final Map<Integer, Object> map = new HashMap<>();

    private final List<ImageRenderInfo> imageRenderInfoList = new ArrayList<>();

    private final List<TextRenderInfo> textRenderInfoList = new ArrayList<>();

    /**
     * 遇到"BT"执行
     */
    @Override
    public void beginTextBlock() {
        log.debug("beginTextBlock");
    }

    /**
     * 最后执行 只执行一次 ，遇到"ET"执行
     */
    @Override
    public void endTextBlock() {
        log.debug("endTextBlock");
    }

    @Override
    public void renderText(TextRenderInfo renderInfo) {
        synchronized (mcidList) {
            log.debug("renderText");
            Integer mcid = renderInfo.getMcid();
            mcidList.add(mcid);
            textRenderInfoList.add(renderInfo);
            map.put(mcid, renderInfo);
        }
    }

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {
        synchronized (mcidList) {
            log.debug("renderImage");
            Integer mcid = renderInfo.getMcid();
            mcidList.add(mcid);
            imageRenderInfoList.add(renderInfo);
            map.put(mcid, renderInfo);
        }
    }
}
