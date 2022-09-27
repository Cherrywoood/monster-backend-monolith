package ru.itmo.monsters.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.monsters.model.auth.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "monster")
public class MonsterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    private Position position;

    @NotEmpty(message = "Name shouldn't be empty")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @NotNull
    @Size(max = 30, message = "Email size shouldn't exceed 30 characters")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(message = "Salary shouldn't be less than 0")
    @Column(name = "salary")
    private int salary;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "monster_rewards",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id")
    )
    private List<RewardEntity> rewards;

    @OneToMany(mappedBy = "monsterEntity", fetch = FetchType.LAZY)
    private List<FearActionEntity> fearActions;

    public enum Gender{
        MALE, FEMALE
    }

    public enum Position{
        SCARER, CLEANER, SCARE_ASSISTANT, DISINFECTOR, RECRUITER
    }
}
