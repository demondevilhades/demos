package test.nlp.ltp;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MainPart {

    private List<SubjectOrObject> subjects;

    private DPTerms predicate;

    private List<SubjectOrObject> objects;

    public MainPart(DPTerms predicate) {
        this.predicate = predicate;
    }

    public MainPart(List<SubjectOrObject> subjects, DPTerms predicate, List<SubjectOrObject> objects) {
        this.subjects = subjects;
        this.predicate = predicate;
        this.objects = objects;
    }

    public List<SubjectOrObject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectOrObject> subjects) {
        this.subjects = subjects;
    }

    public DPTerms getPredicate() {
        return predicate;
    }

    public void setPredicate(DPTerms predicate) {
        this.predicate = predicate;
    }

    public List<SubjectOrObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SubjectOrObject> objects) {
        this.objects = objects;
    }

    public int size() {
        return size(subjects) + size(predicate) + size(objects);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MainPart)) {
            return false;
        }

        MainPart other = (MainPart) obj;
        if (!Objects.equals(this.predicate, other.predicate)) {
            return false;
        }

        if (!ListUtils.isEqualList(this.subjects, other.subjects)) {
            return false;

        }

        return ListUtils.isEqualList(this.objects, other.objects);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.subjects, this.predicate, this.objects);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private int size(List<SubjectOrObject> subjectOrObjects) {
        if (CollectionUtils.isEmpty(subjectOrObjects)) {
            return 0;
        }
        int size = 0;
        for (SubjectOrObject subjectOrObject : subjectOrObjects) {
            size += subjectOrObject.size();
        }
        return size;
    }

    private int size(DPTerms predicate) {
        return predicate == null ? 0 : predicate.size();
    }
}