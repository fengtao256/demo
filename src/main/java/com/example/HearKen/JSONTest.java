package com.example.HearKen;

public class JSONTest {
    public static void main(String[] args) {
        String aa = null ;
        String aa1 = (String) aa ;
        System.out.println("aa1"+aa1);
        String str  = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\ta.comrescatalogid,\n" +
                "\t\t\ta.rescatacode,\n" +
                "\t\t\ta.rescataen,\n" +
                "\t\t\ta.dataclassid,\n" +
                "\t\t\t(\n" +
                "\t\t\t\tSELECT\n" +
                "\t\t\t\t\tcount(1)\n" +
                "\t\t\t\tFROM\n" +
                "\t\t\t\t\tcom_rescatalog\n" +
                "\t\t\t\tWHERE\n" +
                "\t\t\t\t\tcom_rescatalog.dataclassid = a.dataclassid\n" +
                "\t\t\t) AS countonly,\n" +
                "\t\t\tb.name_en,\n" +
                "\t\t\tb.name_item,\n" +
                "\t\t\tb. NAME,\n" +
                "\t\t\t(\n" +
                "\t\t\t\tCASE\n" +
                "\t\t\t\tWHEN b.name_item = b.name_en THEN\n" +
                "\t\t\t\t\t1\n" +
                "\t\t\t\tELSE\n" +
                "\t\t\t\t\t0\n" +
                "\t\t\t\tEND\n" +
                "\t\t\t) AS ifpass,\n" +
                "\t\t\tb.datAtype\n" +
                "\t\tFROM\n" +
                "\t\t\tcom_rescatalog a,\n" +
                "\t\t\tcom_class_item_rel b\n" +
                "\t\tWHERE\n" +
                "\t\t\ta.comrescatalogid = b.rescataid\n" +
                "\t\tAND a.comrescatalogid IN (\n" +
                "\t\t\tSELECT DISTINCT\n" +
                "\t\t\t\t(RESCATALOGID)\n" +
                "\t\t\tFROM\n" +
                "\t\t\t\tSHARE_RESCATALOG_DETAIL\n" +
                "\t\t\tWHERE\n" +
                "\t\t\t\ttype = '00'\n" +
                "\t\t)\n" +
                "\t) a,\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\ta.service_id,\n" +
                "\t\t\ta.servicecode,\n" +
                "\t\t\ta.apply_type,\n" +
                "\t\t\tRESCATALOGID\n" +
                "\t\tFROM\n" +
                "\t\t\tshare_service a,\n" +
                "\t\t\tshare_rescatalog_detail b\n" +
                "\t\tWHERE\n" +
                "\t\t\ta.service_id = b.service_id\n" +
                "\t\tAND a.apply_type IN ('00', '11')\n" +
                "\t\tAND a.state = '11'\n" +
                "\t\tAND b.type = '00'\n" +
                "\t) b\n" +
                "WHERE\n" +
                "\ta.comrescatalogid = b.RESCATALOGID" ;
    }
}
