package com.raven.utils;

import com.raven.core.output.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ServerConfig {

    private static final String UserConfigPath    = "config/server/raven.properties";
    private static final String OperatorConfigDir = "config/operator/operator.properties";

    private final Properties Properties = new Properties();
    private final OperatorConfig OperatorCfg;

    public ServerConfig() {
        this(UserConfigPath);
    }

    public ServerConfig(String UserPath) {
        LoadFromFile(UserPath);
        LoadFromFile(OperatorConfigDir);
        OperatorCfg = new OperatorConfig();
    }


    private void LoadFromFile(String Path) {
        File ConfigFile = new File(Path);
        if (!ConfigFile.exists()) return;
        try (InputStream FileStream = new FileInputStream(ConfigFile)) {
            Properties.load(FileStream);
        } catch (IOException Exception) {
            Logger.Error("Config load failed [" + Path + "]: " + Exception.getMessage());
        }
    }

    private String  StringProperty(String Key)  { return Properties.getProperty(Key, ""); }
    private int     IntProperty(String Key)  { try { return Integer.parseInt(Properties.getProperty(Key, "0").trim()); } catch (NumberFormatException Ignored) { return 0; } }
    private long    LongProperty(String Key) { try { return java.lang.Long.parseLong(Properties.getProperty(Key, "0").trim()); } catch (NumberFormatException Ignored) { return 0L; } }
    private boolean BooleanProperty(String Key) { return Boolean.parseBoolean(Properties.getProperty(Key, "false").trim()); }

    public String Get(String Key)                  { return Properties.getProperty(Key); }
    public String Get(String Key, String Default)  { return Properties.getProperty(Key, Default); }

    public String  GetServerHost()           { return StringProperty("server.host"); }
    public int     GetServerPort()           { return IntProperty("server.port"); }
    public String  GetServerMode()           { return StringProperty("server.mode").toLowerCase(); }
    public String  GetWebHost()              { return StringProperty("web.host"); }
    public int     GetWebPort()              { return IntProperty("web.port"); }
    public String  GetTemplateDir()          { return StringProperty("web.template.dir"); }
    public String  GetStaticDir()            { return StringProperty("web.static.dir"); }
    public int     GetBeaconPort()           { return IntProperty("web.beacon.port"); }
    public int     GetTeamServerPort()       { return IntProperty("teamserver.port"); }
    public String  GetTeamServerApiPrefix()  { String Value = StringProperty("teamserver.api.prefix"); return Value.isEmpty() ? "/api" : Value; }
    public String  GetDatabaseType()         { return StringProperty("db.type").toLowerCase(); }
    public String  GetDatabaseUrl()          { return StringProperty("db.url"); }
    public String  GetDatabaseName()         { return StringProperty("db.name"); }
    public String  GetDatabaseUser()         { return StringProperty("db.user"); }
    public String  GetDatabasePath()         { return StringProperty("db.path"); }
    public String  GetDatabasePassword()     { return StringProperty("db.password"); }
    public String  GetMongoUri()             { return StringProperty("db.mongo.uri"); }
    public String  GetMongoDbName()          { return StringProperty("db.mongo.name"); }
    public long    GetSleepIntervalMs()      { return LongProperty("agent.sleep.interval"); }
    public long    GetJitterMs()             { return LongProperty("agent.jitter.ms"); }
    public int     GetConnectionTimeout()    { return IntProperty("agent.connection.timeout"); }
    public int     GetCommandTimeout()       { return IntProperty("agent.command.timeout"); }
    public int     GetMaxConnections()       { return IntProperty("agent.max.connections"); }
    public int     GetBufferSize()           { return IntProperty("agent.buffer.size"); }
    public String  GetKeystorePath()         { return StringProperty("cert.keystore.path"); }
    public String  GetKeystoreType()         { return StringProperty("cert.keystore.type"); }
    public String  GetKeystorePassword()     { return StringProperty("cert.keystore.password"); }
    public String  GetTruststorePath()       { return StringProperty("cert.truststore.path"); }
    public String  GetTruststoreType()       { return StringProperty("cert.truststore.type"); }
    public String  GetTruststorePassword()   { return StringProperty("cert.truststore.password"); }
    public String  GetCaPath()               { return StringProperty("cert.ca.path"); }
    public String  GetCaType()               { return StringProperty("cert.ca.type"); }
    public String  GetCaPassword()           { return StringProperty("cert.ca.password"); }
    public String  GetAgentCertDir()         { return StringProperty("cert.agent.dir"); }
    public String  GetTlsProtocol()          { return StringProperty("cert.tls.protocol"); }
    public int     GetServerValidityDays()   { return IntProperty("cert.server.validity.days"); }
    public int     GetAgentValidityDays()    { return IntProperty("cert.agent.validity.days"); }
    public int     GetCaValidityDays()       { return IntProperty("cert.ca.validity.days"); }
    public String  GetDnCn()                 { return StringProperty("cert.dn.cn"); }
    public String  GetDnO()                  { return StringProperty("cert.dn.o"); }
    public String  GetDnOu()                 { return StringProperty("cert.dn.ou"); }
    public String  GetDnL()                  { return StringProperty("cert.dn.l"); }
    public String  GetDnSt()                 { return StringProperty("cert.dn.st"); }
    public String  GetDnC()                  { return StringProperty("cert.dn.c"); }
    public String  GetCaDnCn()               { return StringProperty("cert.ca.dn.cn"); }
    public String  GetCaDnO()                { return StringProperty("cert.ca.dn.o"); }
    public String  GetCaDnOu()               { return StringProperty("cert.ca.dn.ou"); }
    public String  GetCaDnL()                { return StringProperty("cert.ca.dn.l"); }
    public String  GetCaDnSt()               { return StringProperty("cert.ca.dn.st"); }
    public String  GetCaDnC()                { return StringProperty("cert.ca.dn.c"); }
    public String  GetLoggingLevel()         { return StringProperty("logging.level").toUpperCase(); }
    public boolean IsVerbose()               { return BooleanProperty("logging.verbose"); }
    public int     GetMaxLogEntries()        { return IntProperty("logging.max.entries"); }
    public String  GetLogFile()              { return StringProperty("logging.file"); }
    public boolean IsFileLoggingEnabled()    { return BooleanProperty("logging.file.enabled"); }
    public String  GetInterfaceMode()        { return StringProperty("mode.interface").toLowerCase(); }
    public String  GetExportDir()            { return StringProperty("export.dir"); }
    public String  GetAgentOutputDir()       { return StringProperty("agent.output.dir"); }
    public String  GetDownloadDir()          { return StringProperty("agent.download.dir"); }
    public boolean IsMtlsEnabled()              { String Mode = GetServerMode(); return Mode.equals("mtls") || Mode.equals("fmtls"); }
    public String  GetDatabaseFilename()        { String Value = StringProperty("db.filename"); return Value.isEmpty() ? GetDatabaseName() + ".db" : Value; }
    public String  GetTeamServerConnectHost()   { String Value = StringProperty("teamserver.connect.host"); return Value.isEmpty() ? "127.0.0.1" : Value; }
    public String[] GetTlsFallbackProtocols()  { String Value = StringProperty("cert.tls.fallback.protocols"); return Value.isEmpty() ? new String[]{"TLSv1.3","TLSv1.2"} : Value.split(","); }
    public int     GetGuiSessionTimeout()       { return IntProperty("gui.session.timeout"); }
    public int     GetGuiLogRetention()         { return IntProperty("gui.log.retention"); }
    public String  GetAdminUsername()        { return OperatorCfg.GetAdminUsername(); }
    public String  GetAdminPassword()        { return OperatorCfg.GetAdminPassword(); }
    public String  GetAdminRole()            { return OperatorCfg.GetAdminRole(); }
}
