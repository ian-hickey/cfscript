@Entity
@Table(name = "known_fruits")
@NamedQuery(name = "Fruits.findAll", query="SELECT * from Fruit;")
@Cacheable
component name="Fruit" {

    @test(name = "1")
    property name="name" value="";

    public void function init(String name) {
        this.name = name;
    }

    public Integer function getId() {
        return id;
    }

    public void function setId(Integer id) {
        this.id = id;
    }

    public String function getName() {
        return name;
    }

    public void function setName(String name) {
        this.name = name;
    }

}