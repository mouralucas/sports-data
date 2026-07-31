import com.rolf.sports_data.enums.EventParticipantSideEnum;
import com.rolf.sports_data.enums.EventParticipantSourceTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "competittion_event_participant_template")
public class CompetitionEventParticipantTemplateEntity extends BaseEntity {

    private CompetitionEventTemplateEntity competitionEventTemplate;

    private EventParticipantSideEnum side;

    private EventParticipantSourceTypeEnum sourceType;

    private CompetitionStageTemplateEntity sourceStageTemplate;

    private CompetitionEventTemplateEntity sourceEventTemplate;

    private Integer sourcePosition;

    private Integer seed;

    private String description;

}