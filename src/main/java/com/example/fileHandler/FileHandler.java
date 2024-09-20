package com.example.fileHandler;

import com.example.product.Category;
import com.example.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FileHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);
    private final File categoryFile = new File("src/main/resources/data/category.json");
    private final File productFile = new File("src/main/resources/data/product.json");

    private final ObjectMapper objectMapper;

    public FileHandler() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public void saveCategory(Category category) {
        try {
            String json = objectMapper.writeValueAsString(category);
            appendToFile(categoryFile, json);
        } catch (IOException e) {
            LOGGER.error("Error saving category: " + e.getMessage(), e);
        }
    }

    public void saveProduct(Product product) {
        try {
            String json = objectMapper.writeValueAsString(product);
            appendToFile(productFile, json);
        } catch (IOException e) {
            LOGGER.error("Error saving product: " + e.getMessage(), e);
        }
    }

    private void appendToFile(File file, String json) throws IOException {
        FileUtils.writeStringToFile(file, json + System.lineSeparator(), "UTF-8", true);
    }

    public Set<Category> loadCategories() {
        try {
            List<String> lines = FileUtils.readLines(categoryFile, "UTF-8");
            return lines.stream()
                    .map(line -> {
                        try {
                            return objectMapper.readValue(line, Category.class);
                        } catch (JsonProcessingException e) {
                            LOGGER.error("Error parsing category JSON: " + e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.error("Error reading categories file: " + e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    public Map<Product, Integer> loadProducts() {
        try {
            List<String> lines = FileUtils.readLines(productFile, "UTF-8");
            return lines.stream()
                    .map(line -> {
                        try {
                            Product product = objectMapper.readValue(line, Product.class);
                            return new AbstractMap.SimpleEntry<>(product, product.getStockQuantity());
                        } catch (JsonProcessingException e) {
                            LOGGER.error("Error parsing product JSON: " + e.getMessage(), e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (IOException e) {
            LOGGER.error("Error reading products file: " + e.getMessage(), e);
            return Collections.emptyMap();
        }
    }
}
