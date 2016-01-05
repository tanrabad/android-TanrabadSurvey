package th.or.nectec.tanrabad.survey.presenter.job.service.jsonentity;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;

import java.util.UUID;

public class UUIDConverter extends StringBasedTypeConverter<UUID> {

    @Override
    public UUID getFromString(String string) {
        return UUID.fromString(string);
    }

    @Override
    public String convertToString(UUID object) {
        return object.toString();
    }
}
