package DeviceMng.devicemng.DTO;

public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;

    public UpdatePasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
