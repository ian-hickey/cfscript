component name="Cfscript" {
    property name="myprop" value="test" type="String";

    function init() {
        writedump("This is a component");
        sayHi = () => writeoutput("Hi");
        five = () => 4+1;
        text = "This is some text";
    }

    function getObjects() {

    }
}