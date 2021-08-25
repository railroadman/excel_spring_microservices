package com.example.customerRep.utils;

import java.util.List;
import java.util.stream.Collectors;

public class MergeBuilder {
    private String target;
    private String source;
    private String on;
    private String on_condition;
    private String matched_then;
    private String when_matched_condition;
    private String when_not_matched;
    private String then_insert;
    private String insert_fields_values;
    private List<String> output_alias;
    private String show_output;


    public static class Builder {
        private String target;
        private String source;
        private String on;
        private String on_condition;
        private String when_matched_condition;
        private String matched_then;
        private String when_not_matched;
        private String then_insert;
        private String insert_fields_values;
        private List<String> output_alias;
        private String show_output;
        private StringBuilder stringBuilder;

        public Builder(String target) {
            stringBuilder = new StringBuilder();
            this.target = stringBuilder.append("MERGE ").append(target).append(" AS TARGET").toString();
            this.on_condition = "";
            this.when_matched_condition = ") WHEN MATCHED ";
            this.matched_then = "";
            this.when_not_matched = " ";
            this.then_insert = "THEN INSERT ";
            this.insert_fields_values = "";
            this.show_output = "";
            stringBuilder.setLength(0);

        }

        public Builder asSource(String source) {
            this.source = stringBuilder.append(" USING ").append(source).append("AS SOURCE").toString();
            stringBuilder.setLength(0);
            return this;
        }

        public Builder on(String target_field, String source_field) {
            this.on = stringBuilder.append(" ON (").append("TARGET.").append(target_field).append("=")
                    .append("SOURCE.").append(source_field).append(" ").toString();
            stringBuilder.setLength(0);
            return this;
        }

        public Builder onCondition(String target_field, String predicate, String source_field) {
            this.on_condition += stringBuilder.append(" AND ").append(" TARGET.").append(target_field).append(predicate)
                    .append(" SOURCE.").append(source_field).toString();
            stringBuilder.setLength(0);
            return this;
        }

        public Builder whenMatched(String and_or, String target_field, String predicate, String source_field) {
            this.when_matched_condition += stringBuilder.append(" ").append(and_or).append(" ")
                    .append("TARGET.").append(target_field).append(predicate).
                    append("SOURCE.").append(source_field).toString();
            stringBuilder.setLength(0);
            return this;
        }
        public Builder whenNotMatched(){
            this.when_not_matched = " WHEN NOT MATCHED BY TARGET ";
            return this;
        }

       /* public Builder setOutputAlias(List<String> fields,prefix){
            String result = fields.stream().collect(Collectors.joining(","+prefix,prefix,""));

        }
*/

        public Builder matchedThenUpdate() {
            this.matched_then += stringBuilder.append(" THEN UPDATE SET ");
            stringBuilder.setLength(0);
            return this;
        }

        public Builder insertFields(List<String> fields, List<String> values, String prefix) {

            String result = values.stream().collect(Collectors.joining(","+prefix,prefix,""));
            this.insert_fields_values += stringBuilder.append("(").append(String.join(",", fields))
                    .append(")").append(" VALUES (").append(result)
                    .append(")").toString();
            stringBuilder.setLength(0);
            return this;

        }

        public Builder matchedThenUpdateCondition(List<String> fields) {
            int i = 0;
            String separator = ",";
            for (String field : fields) {

                if (i++ == fields.size() - 1) {
                    separator = "";
                }
                this.matched_then += stringBuilder.append("TARGET.").append(field).append("=")
                        .append("SOURCE.").append(field).append(separator).toString();
                stringBuilder.setLength(0);

            }

            return this;
        }

        public Builder showOutput(){
            this.show_output = "OUTPUT $action,";
            return this;
        }


        public MergeBuilder build() {
            MergeBuilder mergeBuilder = new MergeBuilder();
            mergeBuilder.target = this.target;
            mergeBuilder.source = this.source;
            mergeBuilder.on = this.on;
            mergeBuilder.on_condition = this.on_condition;
            mergeBuilder.when_matched_condition = this.when_matched_condition;
            mergeBuilder.matched_then = this.matched_then;
            mergeBuilder.when_not_matched = this.when_not_matched;
            mergeBuilder.then_insert = this.then_insert;
            mergeBuilder.insert_fields_values = this.insert_fields_values;

            return mergeBuilder;
        }


    }

    private MergeBuilder() {
    }

    ;

    @Override
    public String toString() {
        return target + source + on + on_condition + when_matched_condition + matched_then + when_not_matched + then_insert + insert_fields_values;
    }
}
