package eu.sanjin.kurelic.cbc.view.components;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class PaginationBuilder {

    private static final String SLASH = "/";

    public static Map<String, ?> buildPagination(
            Long numberOfItems,
            Optional<Integer> pageNumber,
            String leftUrlPart
    ) {
        return buildPaginationWithAppender(AttributeNames.PAGINATION_APPENDER_EMPTY, numberOfItems, pageNumber,
                leftUrlPart, StringUtils.EMPTY);
    }

    public static Map<String, ?> buildPagination(
            Long numberOfItems,
            Optional<Integer> pageNumber,
            String leftUrlPart,
            String rightUrlPart
    ) {
        return buildPaginationWithAppender(AttributeNames.PAGINATION_APPENDER_EMPTY, numberOfItems, pageNumber,
                leftUrlPart, rightUrlPart);
    }

    public static Map<String, ?> buildPaginationWithAppender(
            String appender,
            Long numberOfItems,
            Optional<Integer> pageNumber,
            String leftUrlPart,
            String rightUrlPart
    ) {
        Map<String, Object> attributes = new HashMap<>();
        pageNumber = Optional.of(getValidPageNumber(pageNumber, numberOfItems));
        if (leftUrlPart.startsWith(SLASH)) {
            leftUrlPart = leftUrlPart.substring(1);
        }
        if (!rightUrlPart.isBlank() && !rightUrlPart.startsWith(SLASH)) {
            rightUrlPart = SLASH + rightUrlPart;
        }
        var currentPage = pageNumber.orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
        attributes.put(AttributeNames.PAGINATION_NUMBER_OF_ITEMS + appender, numberOfItems);
        attributes.put(AttributeNames.PAGINATION_CURRENT_PAGE + appender, currentPage);
        attributes.put(AttributeNames.PAGINATION_LEFT_URL_PART + appender, leftUrlPart);
        attributes.put(AttributeNames.PAGINATION_RIGHT_URL_PART + appender, rightUrlPart);

        return attributes;
    }

    public static int getValidPageNumber(Optional<Integer> pageNumber, Long numberOfItems) {
        return pageNumber.map(integer -> ExpressionLanguageFunctions.checkAndGetCurrentPage(
                integer,
                ExpressionLanguageFunctions.getNumberOfPages(numberOfItems)
        )).orElse(VisibleConfiguration.FIRST_DEFAULT_PAGINATION_ITEM);
    }

}
