module azizollahi.app.socks.core {
    exports azizollahi.app.socks.core;
    requires azizollahi.app.Utility;
    requires azizollahi.app.interfaces;
    requires azizollahi.app.channeling;
    requires azizollahi.app.config;
    requires azizollahi.app.flow;
    requires log4j;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires java.sql;
}