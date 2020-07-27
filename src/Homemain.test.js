import React from "react";
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Homemain from "./components/Homemain";
Enzyme.configure({ adapter: new Adapter() });


describe('createurl', () =>
{
    // 检验开始状态
    it('包含一个input,button,text', () =>
    {
        const wrapper = shallow(<Homemain />);
        const inputElem = wrapper.find("Input");
        expect(inputElem.length).toBe(1);
        const ButtonElem = wrapper.find("Button");
        expect(ButtonElem.length).toBe(1);
        const textElem = wrapper.find("text");
        expect(textElem.length).toBe(1);
    });

    it('input初始化为空,text初始化', () =>
    {
        const wrapper = shallow(<Homemain />);
        const inputElem = wrapper.find("Input");
        expect(inputElem.prop('value')).toEqual('');
        expect(wrapper.state('shorturl')).toEqual('');
    })

    // case1
    // 通过查找存在 Input,测试组件正常渲染并显示

    it('input 输入发生变化', () =>
    {

        const wrapper = shallow(<Homemain />);
        const inputElem = wrapper.find("Input");
        inputElem.simulate('change', {
            target: { value: 'http://baidu.com' }
        })
        expect(wrapper.state('oriurl')).toEqual('http://baidu.com');
    })

    it('按button 符合格式 调用handleCount函数', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Homemain handleCount={fn} />);
        const inputElem = wrapper.find("Input");
        const ButtonElem = wrapper.find("Button");
        inputElem.simulate('change', {
            target: { value: 'http://www.baidu.com' }
        });

        ButtonElem.simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('按button 不符合格式 不调用handleCount函数', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Homemain handleCount={fn} />);
        const inputElem = wrapper.find("Input");
        const ButtonElem = wrapper.find("Button");
        inputElem.simulate('change', {
            target: { value: 'baidu.com' }
        });

        ButtonElem.simulate('click');
        expect(fn).not.toHaveBeenCalled();
    })

    it("test method string10to62()", () =>
    {
        const homemain = new Homemain();
        expect(homemain.string10to62(10000)).toEqual("2Bi");
    });
})