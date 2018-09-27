module azizollahi.app.flow {
    exports azizollahi.app.socks.flow.handlers;
    exports azizollahi.app.socks.flow.types;
    exports azizollahi.app.socks.flow;
    requires azizollahi.app.interfaces;
    requires azizollahi.app.config;
    requires azizollahi.app.channeling;
    requires azizollahi.app.Utility;
}