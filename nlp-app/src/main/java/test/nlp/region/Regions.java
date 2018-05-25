package test.nlp.region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import test.nlp.common.LocationTerm;
import test.nlp.common.Term;
import test.nlp.dict.pos.POS;

/**
 * 中国行政区划信息
 */
public class Regions {
    public static final String PATH = "region";

    public static final String FILE_REGIONS_DIC = "regions.dic";

    protected Map<String, Region> code2Regions;

    protected Multimap<String, Region> name2Regions;

    protected Regions() {
    }

    public Regions(List<Region> regions) {
        this.code2Regions = Maps.newHashMap();
        this.name2Regions = ArrayListMultimap.create();
        for (Region region : regions) {
            this.code2Regions.put(region.getCode(), region);
            name2Regions.put(region.getName(), region);
            name2Regions.put(region.getShortName(), region);
        }

        for (Region region : regions) {
            if (region.getParentCode() == null) {
                continue;
            }
            region.setParentRegion(this.code2Regions.get(region.getParentCode()));
        }
    }

    public Region getRegionByCode(String code) {
        return code2Regions.get(code);
    }

    /**
     * 合并地区信息
     * 
     * @param domain
     * @param defaultRegion
     * @param terms
     */
    public void merge(Region defaultRegion, LinkedList<Term> terms) {
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
            if (curRegions.size() > 1) {
                curRegions = filterRegions(curRegions, defaultRegion);
            }
            ((LocationTerm) curTerm).setRegions(curRegions);
        }
    }

    private Collection<Region> filterRegions(Collection<Region> regions, Region defaultRegion) {
        List<Region> filteredRegions = Lists.newArrayListWithCapacity(regions.size());
        for (Region region : regions) {
            if (region.isBelong(defaultRegion)) {
                filteredRegions.add(region);
            }
        }
        return filteredRegions.isEmpty() ? regions : filteredRegions;
    }

    protected void filterRegions(Collection<Region> regions, Collection<Region> prevRegions) {
        regions.removeIf((region) -> {
            for (Region prevRegion : prevRegions) {
                if (region.isBelong(prevRegion)) {
                    return false;
                }
            }
            return true;
        });
    }

    protected Collection<Region> getRegions(Term term) {
        if (!(term instanceof LocationTerm)) {
            return Collections.emptyList();
        }
        Collection<Region> regions = name2Regions.get(term.getWord());
        if (regions.isEmpty() && term.getSynonyms() != null) {
            for (String synonym : term.getSynonyms()) {
                regions = name2Regions.get(synonym);
                if (!regions.isEmpty()) {
                    break;
                }
            }
        }
        // bug fix
        Collection<Region> newRegions = new ArrayList<Region>(regions.size());
        newRegions.addAll(regions);
        return newRegions;
    }

    // add by zs

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
