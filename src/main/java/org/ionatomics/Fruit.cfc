@Entity
@Table(name = "known_fruits")
@NamedQuery(name = "Fruits.findAll", query = "SELECT f FROM Fruit f ORDER BY f.name", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable
component name="Fruit" {

    @Id
    @SequenceGenerator(name = "fruitsSequence", sequenceName = "known_fruits_id_seq", allocationSize = 1, initialValue = 10)
    @GeneratedValue(generator = "fruitsSequence")
    property name="id" type="int";

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