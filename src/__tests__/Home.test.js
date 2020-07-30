import React from "react";
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Home from "../view/Home.js";
Enzyme.configure({ adapter: new Adapter() });

describe('Home 正确渲染', () =>
{
    it('包含一个layout 和div', () =>
    {
        const wrapper = shallow(<Home />);
        const layoutElem = wrapper.find("layout");
        expect(layoutElem.length).toBe(1);
        const divElem = wrapper.find("div");
        expect(divElem.find(".Contain").length).toBe(1);
        expect(divElem.find(".Contain-side").length).toBe(1);
        expect(divElem.find(".Contain-main").length).toBe(1);
    });
})