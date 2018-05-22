package hades.datatransfer;

public interface Output<DataType> extends AutoCloseable {

    public void output(DataType dataType) throws Exception;
}
