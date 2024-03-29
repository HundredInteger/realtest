package com.example.foodmap.model;

import com.example.foodmap.dto.meeting.MeetingCreatRequestDto;
import com.example.foodmap.validator.MeetingValidator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Meeting extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private String restaurant;

    @Column(nullable = false)
    private String meetingTitle;

    @Column(nullable = false)
    private String content;

    @Embedded
    private Location location;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul" )
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul" )
    private LocalDateTime endDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime meetingDate;

    @ColumnDefault(value = "0")
    private int viewCount;

    @Column
    private int limitPeople;

    @OneToMany(mappedBy = "meeting",cascade = CascadeType.ALL)
    List<MeetingParticipate> meetingParticipates = new ArrayList<>();

    @OneToMany(mappedBy = "meeting",cascade = CascadeType.ALL)
    List<MeetingComment>meetingComments = new ArrayList<>();

    @ColumnDefault(value = "0")
    private int nowPeople;


    public Meeting(User user,MeetingCreatRequestDto meetingCreatRequestDto) {

        MeetingValidator.isValidMeeting(user,meetingCreatRequestDto);

        this.user=user;
        this.restaurant=meetingCreatRequestDto.getRestaurant();
        this.meetingTitle=meetingCreatRequestDto.getMeetingTitle();
        this.content=meetingCreatRequestDto.getContent();
        this.location=meetingCreatRequestDto.getLocation();
        this.startDate=meetingCreatRequestDto.getStartDate();
        this.endDate=meetingCreatRequestDto.getEndDate();
        this.meetingDate=meetingCreatRequestDto.getMeetingDate();
        this.limitPeople=meetingCreatRequestDto.getLimitPeople();
        this.nowPeople=meetingCreatRequestDto.getNowPeople();

    }
    //모임수정(취소예정)
    public void update(MeetingCreatRequestDto meetingCreatRequestDto) {
        this.restaurant=meetingCreatRequestDto.getRestaurant();
        this.meetingTitle=meetingCreatRequestDto.getMeetingTitle();
        this.content=meetingCreatRequestDto.getContent();
        this.location=meetingCreatRequestDto.getLocation();
        this.startDate=meetingCreatRequestDto.getStartDate();
        this.endDate=meetingCreatRequestDto.getEndDate();
        this.meetingDate=meetingCreatRequestDto.getMeetingDate();
        this.limitPeople=meetingCreatRequestDto.getLimitPeople();
        this.nowPeople=meetingCreatRequestDto.getNowPeople();
    }

    public void addnowPeople() {
        this.nowPeople = nowPeople+1;
    }
    public void subnowPeople() {
        this.nowPeople = nowPeople-1;
    }
}
