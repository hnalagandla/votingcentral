/*
 * Created on Aug 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.chart;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;


/**
 * A custom label generator.
 */
class LabelGenerator extends StandardCategoryItemLabelGenerator {
    /**
     * Generates an item label.
     * 
     * @param dataset  the dataset.
     * @param series  the series index.
     * @param category  the category index.
     * 
     * @return the label.
     */
    public String generateItemLabel(CategoryDataset dataset, 
                                    int series, 
                                    int category) {
        return dataset.getRowKey(series).toString();
    }
}