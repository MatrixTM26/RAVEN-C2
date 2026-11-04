package com.raven.utils;

import com.raven.core.output.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public final class OperatorConfig {

    private final Properties Props    = new Properties();
    private final String     FilePath;

    public OperatorConfig() {
        this(RavenConstants.OperatorConfigPath);
    }

    public OperatorConfig(String Path) {
        this.FilePath = Path;
        LoadFromFile(Path);
    }

    OperatorConfig(Properties Merged) {
        this.FilePath = RavenConstants.OperatorConfigPath;
        Merged.forEach((Key, Value) -> Props.setProperty(Key.toString(), Value.toString()));
    }

    private void LoadFromFile(String Path) {
        File F = new File(Path);
        if (!F.exists()) return;
        try (InputStream In = new FileInputStream(F)) {
            Props.load(In);
        } catch (IOException E) {
            Logger.Error("OperatorConfig: failed to load " + Path + " — " + E.getMessage());
        }
    }

    public boolean Save() {
        return Save(FilePath);
    }

    public boolean Save(String Path) {
        try {
            File F = new File(Path);
            F.getParentFile().mkdirs();
            try (FileOutputStream Out = new FileOutputStream(F)) {
                Props.store(Out, "RAVEN Operator Configuration");
            }
            return true;
        } catch (IOException E) {
            Logger.Error("OperatorConfig: failed to save " + Path + " — " + E.getMessage());
            return false;
        }
    }

    public String GetFilePath()              { return FilePath; }
    public String GetOperatorName()          { return StringProperty("operator.name"); }
    public String GetOperatorRole()          { return StringProperty("operator.role").toUpperCase(); }
    public String GetTheme()                 { return StringProperty("operator.theme"); }
    public String GetPromptColor()           { return StringProperty("operator.prompt.color"); }
    public boolean IsOutputBoxEnabled()      { return BooleanProperty("operator.output.box"); }
    public boolean IsTimestampEnabled()      { return BooleanProperty("operator.output.timestamp"); }
    public int GetSessionLogLimit()          { return IntProperty("operator.session.log.limit"); }
    public int GetHistoryLimit()             { return IntProperty("operator.history.limit"); }
    public boolean IsAutoReconnect()         { return BooleanProperty("operator.auto.reconnect"); }
    public boolean IsChatNotifyEnabled()     { return BooleanProperty("operator.chat.notify"); }
    public String GetTimezone()              { return StringProperty("operator.timezone"); }
    public String GetDateFormat()            { return StringProperty("operator.date.format"); }
    public String GetInteractiveExitCmd()    { return StringProperty("operator.interactive.exit"); }
    public boolean IsBroadcastConfirm()      { return BooleanProperty("operator.broadcast.confirm"); }
    public boolean IsSelfDestructConfirm()   { return BooleanProperty("operator.selfdestruct.confirm"); }

    public void SetOperatorName(String Name) { Props.setProperty("operator.name", Name); }
    public void SetOperatorRole(String Role) { Props.setProperty("operator.role", Role.toUpperCase()); }
    public void SetTheme(String Theme)       { Props.setProperty("operator.theme", Theme); }

    public String  Get(String Key, String Default) { return Props.getProperty(Key, Default); }
    public void Put(String Key, String Value)    { Props.setProperty(Key, Value); }

    public String GetAdminUsername()  { return StringProperty("admin.username"); }
    public String GetAdminPassword()  { return StringProperty("admin.password"); }
    public String GetAdminRole()      { String Role = StringProperty("admin.role").toUpperCase(); return Role.isEmpty() ? "SUPER" : Role; }

    public Map<String, String> ToMap() {
        Map<String, String> M = new LinkedHashMap<>();
        Props.forEach((K, V) -> M.put(K.toString(), V.toString()));
        return M;
    }

    private String StringProperty(String Key)  { return Props.getProperty(Key, ""); }
    private int    IntProperty(String Key)  {
        try { return Integer.parseInt(Props.getProperty(Key, "0").trim()); }
        catch (NumberFormatException Ignored) { return 0; }
    }
    private boolean BooleanProperty(String Key) {
        return Boolean.parseBoolean(Props.getProperty(Key, "false").trim());
    }
}
