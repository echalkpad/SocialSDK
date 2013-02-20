/**
 * JQuery implementation of the JSON methods.
 * @module json
 */
define(['jquery'],function($) {
    return {
        /**
         * Parses a String of JSON and returns a JSON Object.
         * @param {String} jsonString A String of JSON.
         * @returns {Object} A JSON Object.
         * @static
         */
        parse : function(jsonString) {
            return $.parseJSON(jsonString);
        },
        
        /**
         * Returns the JSON object represented as a String.
         * @param {Object} jsonObj A JSON Object.
         * @returns {String} The JSON Object represented as a String.
         */
        stringify : function(jsonObj) {
            return JSON.stringify(jsonObj);
        }
    };
});