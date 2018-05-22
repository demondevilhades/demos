package hades.datatransfer;

public class Transfer {

    public static <DataType, I extends Input<DataType>, O extends Output<DataType>> void transfer(I i, O o)
            throws Exception {
        DataType dataType = i.input();
        while (dataType != null) {
            o.output(dataType);
            dataType = i.input();
        }
        o.close();
        i.close();
    }
}
