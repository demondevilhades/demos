package hades.datatransfer;

public interface Input<DataType> extends AutoCloseable {

    public DataType input() throws Exception;

    public void inputWapper(Output<String> output) throws Exception;

    public interface InputWapper<DataType> {
        public void wapper(DataType dataType) throws Exception;
    }
}
