module.exports = ({
    <#list data?keys as key>
        "${key}":"${data[key]}"
    </#list>
});