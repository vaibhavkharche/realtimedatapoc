package com.maveric.realtimedatapoc.util;

import com.maveric.realtimedatapoc.entity.Order;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class CsvReaderUtil {

    private CsvReaderUtil() { }

    public static List<Order> readOrderFromCsv() throws IOException {
        List<Order> orders = new ArrayList<>();
        File file = ResourceUtils.getFile("classpath:Orders.csv");
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
                .withSkipLines(1)
                .build();
        List<String[]> allData = csvReader.readAll();
        for (String[] row : allData) {
            Order order = new Order();
            order.setId(Long.valueOf(row[0].trim()));
            order.setProductName(row[1].trim());
            order.setCustomerName(row[2]);
            if(StringUtils.hasText(row[3]))
                order.setProductId(Long.valueOf(row[3]));
            if(StringUtils.hasText(row[4]))
                order.setPrice(Double.valueOf(row[4]));
            if(StringUtils.hasText(row[5]))
                order.setDiscount(Double.valueOf(row[5]));
            order.setTerritory(row[6]);
            order.setCategory(row[7]);
            if(StringUtils.hasText(row[8]))
                order.setGrade(Double.valueOf(row[8]));
            orders.add(order);
        }
        return orders;
    }
}
