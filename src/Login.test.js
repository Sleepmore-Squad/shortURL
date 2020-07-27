import React from "react";
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Login from "./view/Login";
import Fetch from "./fetch";
Enzyme.configure({ adapter: new Adapter() });

jest.mock("./fetch");
const mockFetchSubmitData = jest.fn()
    .mockReturnValueOnce(201)
    .mockReturnValueOnce(401)
    .mockReturnValueOnce(101);



describe('creaturl', () =>
{
    beforeAll(() => 
    {
        Fetch.mockImplementation(() => ({
            fetchSubmitData: mockFetchSubmitData
        }))
    });

    beforeEach(() => 
    {
        mockFetchSubmitData.mockClear();
        Fetch.mockClear();
    });

    // 检验开始状态
    it('包含2个input,button', () =>
    {
        const wrapper = shallow(<Login />);
        const inputElem = wrapper.find("Input");
        expect(inputElem.length).toBe(2);
        const ButtonElem = wrapper.find("Button");
        expect(ButtonElem.length).toBe(2);
    });

    it('input初始化为空,text初始化', () =>
    {
        const wrapper = shallow(<Login />);
        expect(wrapper.state('password')).toEqual('');
        expect(wrapper.state('username')).toEqual('');
    })

    it('input 输入发生变化', () =>
    {

        const wrapper = shallow(<Login />);
        const inputElem = wrapper.find("Input");
        inputElem.find(".input").simulate('change', {
            target: { value: 'dxy' }
        });
        expect(wrapper.state('username')).toEqual('dxy');

        inputElem.find(".inputpassword").simulate('change', {
            target: { value: '12345' }
        });
        expect(wrapper.state('password')).toEqual('12345');
    })

    it('用户名为空,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Login usernameisnull={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find(".inputpassword").simulate('change', {
            target: { value: '123456' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find(".login1").simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('密码为空,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Login passwordisnull={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find(".input").simulate('change', {
            target: { value: 'dxy' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find(".login1").simulate('click');
        expect(fn).toHaveBeenCalled();
    })



    it('用户名密码正确跳转到home页面', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Login login1={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find(".input").simulate('change', {
            target: { value: 'dxy' }
        });
        inputElem.find(".inputpassword").simulate('change', {
            target: { value: '12345' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find(".login1").simulate('click');
        expect(fn).toHaveBeenCalled();

    })

    it('用户名密码正确跳转到adminhome页面', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Login login2={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find(".input").simulate('change', {
            target: { value: 'dxy' }
        });
        inputElem.find(".inputpassword").simulate('change', {
            target: { value: '12345' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find(".login1").simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('用户名密码错误弹出提示', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Login login3={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find(".input").simulate('change', {
            target: { value: 'dxy' }
        });
        inputElem.find(".inputpassword").simulate('change', {
            target: { value: '12345' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find(".login1").simulate('click');
        expect(fn).toHaveBeenCalled();

    })

    it('按register进入注册页面', () =>
    {
        //测试的时候需要屏蔽掉history.replace
        const fn = jest.fn(() =>
        {
            return 1;
        });
        const wrapper = shallow(<Login register1={fn} />);

        const ButtonElem = wrapper.find("Button");
        ButtonElem.find(".register").simulate('click');
        expect(fn).toHaveBeenCalled();
    })


})