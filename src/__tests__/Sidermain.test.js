import React from 'react';
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Sidermain from "../components/Sidermain";
Enzyme.configure({ adapter: new Adapter() });

const mockHistory = { replace: jest.fn() };
describe('Homemain', () =>
{

    // 检验开始状态
    it('包含sider和menu', () =>
    {
        const wrapper = shallow(<Sidermain />);
        const SiderElem = wrapper.find("Sider");
        expect(SiderElem.length).toBe(1);
        const MenuElem = wrapper.find(".menu");
        expect(MenuElem.length).toBe(1);
        const HomeElem = wrapper.find(".Home");
        expect(HomeElem.length).toBe(1);
        const SetElem = wrapper.find(".Set");
        expect(SetElem.length).toBe(1);
    });

    //click正常
    it('按home 调用handlehome函数', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Sidermain home={fn} history={mockHistory} />);
        const HomeElem = wrapper.find(".Home");
        HomeElem.simulate('click');
        expect(fn).toHaveBeenCalled();
    });

    it('按set 调用handleset函数', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Sidermain set={fn} history={mockHistory} />);
        const HomeElem = wrapper.find(".Set");
        HomeElem.simulate('click');
        expect(fn).toHaveBeenCalled();
    });

})