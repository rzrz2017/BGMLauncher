package com.andy.music;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "COMCOMMAND".
 */
public class COMCommand {

    private Long id;
    private String commandName;
    private byte[] commandBytes;
    private Long deviceId;

    public COMCommand() {
    }

    public COMCommand(Long id) {
        this.id = id;
    }

    public COMCommand(Long id, String commandName, byte[] commandBytes, Long deviceId) {
        this.id = id;
        this.commandName = commandName;
        this.commandBytes = commandBytes;
        this.deviceId = deviceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public byte[] getCommandBytes() {
        return commandBytes;
    }

    public void setCommandBytes(byte[] commandBytes) {
        this.commandBytes = commandBytes;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

}
