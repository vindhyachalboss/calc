package com.sapient.feecalculator;

import com.sapient.feecalculator.Constant.FILE_TYPE;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface TransactionReader {

    List<Transaction> read(String filePath) throws IOException;

    static List<Transaction> readFile(FILE_TYPE fileType, String filePath) throws IOException {
        switch (fileType) {
            case CSV:
                return csvTrxReader().read(filePath);
            case TEXT:
                return textTrxReader().read(filePath);
            case XML:
                return xmlTrxReader().read(filePath);

            default:
                return null;
        }
    }

    static TransactionReader textTrxReader() {
        return transactionFile -> {
            List<Transaction> list = new ArrayList<>();
            String line = "";
            String cvsSplitBy = ",";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(TransactionReader.class.getClassLoader().getResourceAsStream(transactionFile)));) {
                while ((line = br.readLine()) != null) {
                    String[] transactionAttributes = line.split(cvsSplitBy);
                    Transaction transaction = Utils.getTransaction(transactionAttributes);
                    list.add(transaction);
                }
                return list;
            }
        };
    }

    static TransactionReader xmlTrxReader() {
        throw new RuntimeException("not yet implemented");
    }

    static TransactionReader csvTrxReader() {
        throw new RuntimeException("not yet implemented");
    }

}
