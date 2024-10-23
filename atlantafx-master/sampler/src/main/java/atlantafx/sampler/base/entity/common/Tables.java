package atlantafx.sampler.base.entity.common;

public class Tables {

  private int id;
  private String name;
  private String statusId;


  public Tables(String name, String statusId) {

    this.name = name;
    this.statusId = statusId;
  }

  public String getStatusId() {
    return statusId;
  }

  public void setStatusId(String statusId) {
    this.statusId = statusId;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
