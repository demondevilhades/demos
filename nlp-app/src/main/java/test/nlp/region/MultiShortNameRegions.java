package test.nlp.region;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import test.nlp.common.LocationTerm;
import test.nlp.common.Term;
import test.nlp.dict.pos.POS;

/**
 * 中国行政区划信息
 * 
 * @author zs
 */
public class MultiShortNameRegions extends Regions {

    public MultiShortNameRegions(List<MultiShortNameRegion> regions) {
        this.code2Regions = Maps.newHashMap();
        this.name2Regions = ArrayListMultimap.create();
        List<String> shortNameList;
        for (MultiShortNameRegion region : regions) {
            this.code2Regions.put(region.getCode(), region);
            name2Regions.put(region.getName(), region);
            shortNameList = region.getShortNameList();
            if (shortNameList != null) {
                for (String shortName : shortNameList) {
                    name2Regions.put(shortName, region);
                }
            }
        }

        for (Region region : regions) {
            if (region.getParentCode() == null) {
                continue;
            }
            region.setParentRegion(this.code2Regions.get(region.getParentCode()));
        }
    }

    /**
     * 合并地区信息
     * 
     * @param terms
     */
    public void merge(LinkedList<Term> terms) {
        ListIterator<Term> iterator = terms.listIterator();

        Term curTerm = null;
        Term nextTerm = null;
        Collection<Region> curRegions = null;
        Collection<Region> nextRegions = null;
        StringBuilder locationText = new StringBuilder();

        while (iterator.hasNext()) {
            curTerm = iterator.next();
            curRegions = getRegions(curTerm);
            if (curRegions.isEmpty()) {
                continue;
            }
            locationText.setLength(0);
            while (iterator.hasNext()) {
                nextTerm = iterator.next();

                nextRegions = getRegions(nextTerm);
                if (nextRegions.isEmpty()) {
                    break;
                }
                filterRegions(nextRegions, curRegions);
                if (nextRegions.isEmpty()) {
                    iterator.previous();
                    break;
                }

                if (locationText.length() == 0) {
                    locationText.append(curTerm.getWord());
                }
                locationText.append(nextTerm.getWord());
                curRegions = nextRegions;
                iterator.remove();
            }

            if (locationText.length() > 0) {
                ((LocationTerm) curTerm).setWord(locationText.toString());
                curTerm.setPos(POS.ns);
                curTerm.setFreq(1);
            }
            ((LocationTerm) curTerm).setRegions(curRegions);
        }
    }
}
