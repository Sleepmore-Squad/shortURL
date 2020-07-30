import React from 'react';
import Enzyme ,{shallow}from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Router from "../Router";
Enzyme.configure({adapter:new Adapter()});

describe('Routertest', () => {

    // 检验开始状态
    it('渲染正确', () => {
        const wrapper = shallow(<Router/>);
        const RouterElem = wrapper.find("Router");
        expect(RouterElem.length).toBe(1);
        const RouteElem = wrapper.find("Route");
        expect(RouteElem.length).toBe(5);
        const SwitchElem = wrapper.find("Switch");
        expect(SwitchElem.length).toBe(1);
        const RedirectElem = wrapper.find("Redirect");
        expect(RedirectElem.length).toBe(1);
    });
})