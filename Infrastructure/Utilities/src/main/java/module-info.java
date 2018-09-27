module azizollahi.app.Utility {
    exports azizollahi.app.socks.Utility;

    requires azizollahi.app.config;
    requires log4j;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
}