package com.rosterreview.data;

/**
 * A class that describes a person's full name.
 */
public class PersonName {

    private String firstName;

    private String nickname;

    private String middleName;

    private String lastName;

    private String suffix;

    /**
     * A public constructor for {@link PersonName}.
     *
     * @param firstName   a person's first name
     * @param nickname    a person's nickname
     * @param middleName  a person's middle name
     * @param lastName    a person's last name
     * @param suffix      a person's last name suffix
     */
    public PersonName(String firstName, String nickname, String middleName,
            String lastName, String suffix) {
        this.firstName = firstName;
        this.nickname = nickname;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
    }

    /**
     * @return  a person's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName a person's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return  a person's nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname  a person's nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return  a person's middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName  a person's middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return  a person's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName  a person's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return  a person's last name suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix  a person's last name suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
