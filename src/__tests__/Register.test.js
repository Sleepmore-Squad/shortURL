import React from "react";
import Enzyme, { shallow } from 'enzyme';
import Adapter from 'enzyme-adapter-react-16';
import Register from "../view/Register";
import Fetch from "../fetch";
Enzyme.configure({ adapter: new Adapter() });

jest.mock("../fetch");
const mockFetchEmailSend = jest.fn();
const mockFetchCheckCode = jest.fn()
    .mockReturnValueOnce(101)
    .mockReturnValue(102);

const mockFetchUserPasswordAndEmail = jest.fn()
    .mockReturnValueOnce(111)
    .mockReturnValueOnce(110)
    .mockReturnValueOnce(101)
    .mockReturnValueOnce(100);

const mockHistory = { replace: jest.fn() };


describe('register', () =>
{
    beforeAll(() => 
    {
        Fetch.mockImplementation(() => ({
            fetchEmailSend: mockFetchEmailSend,
            fetchCheckCode: mockFetchCheckCode,
            fetchUserPasswordAndEmail: mockFetchUserPasswordAndEmail
        }))
    });

    beforeEach(() => 
    {
        mockFetchEmailSend.mockClear();
        mockFetchCheckCode.mockClear();
        mockFetchUserPasswordAndEmail.mockClear();
        Fetch.mockClear();
    });
    // 检验开始状态
    it('包含5个input,3个button', () =>
    {
        const wrapper = shallow(<Register />);
        const inputElem = wrapper.find("Input");
        expect(inputElem.length).toBe(5);
        const ButtonElem = wrapper.find("Button");
        expect(ButtonElem.length).toBe(3);
    });

    it('input初始化为空,text初始化', () =>
    {
        const wrapper = shallow(<Register />);
        expect(wrapper.state('password1')).toEqual('');
        expect(wrapper.state('password2')).toEqual('');
        expect(wrapper.state('username')).toEqual('');
        expect(wrapper.state('email')).toEqual('');
        expect(wrapper.state('code')).toEqual('');
    })

    it('input 输入发生变化', () =>
    {

        const wrapper = shallow(<Register />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });
        expect(wrapper.state('username')).toEqual('dxy');

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });
        expect(wrapper.state('password1')).toEqual('123456');

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });
        expect(wrapper.state('password2')).toEqual('123456');

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '123456@qq.com' }
        });
        expect(wrapper.state('email')).toEqual('123456@qq.com');

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });
        expect(wrapper.state('code')).toEqual('12TS8');

    })

    it('按下发送验证码，调用emailtest函数', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register codesend={fn} />);
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="send-code"]').simulate('click');
        expect(fn).toHaveBeenCalled();
        expect(mockFetchEmailSend).toBeCalled();
    })

    it('按下cancel，返回login页面', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register cancel={fn} history={mockHistory} />);
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="cancel"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    //先判断用户名、密码、邮箱、验证码是否为空报错
    it('用户名为空,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register usernameisnull={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: '' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '123456@qq.com' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('密码为空,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register password1isnull={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '123456@qq.com' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('email为空,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register emailisnull={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('验证码为空,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register codeisnull={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '12345@qq.com' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    });

    it('email格式错误,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register emailerror={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: 'qq.com' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it('密码输入不一致,弹出报错', () =>
    {
        const fn = jest.fn();
        const wrapper = shallow(<Register passwordnotequal={fn} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '12345@qq.com' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });
        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn).toHaveBeenCalled();
    })

    it("填写均正确，成功注册", () => 
    {
        const fn1 = jest.fn();
        const fn2 = jest.fn();
        const fn3 = jest.fn();
        const fn4 = jest.fn();
        const wrapper = shallow(<Register history={mockHistory} UEExist={fn1} UExist={fn2} EExist={fn3} success={fn4} />);
        const inputElem = wrapper.find("Input");
        inputElem.find('[test-data="username"]').simulate('change', {
            target: { value: 'dxy' }
        });

        inputElem.find('[test-data="password1"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="password2"]').simulate('change', {
            target: { value: '123456' }
        });

        inputElem.find('[test-data="email"]').simulate('change', {
            target: { value: '12345@qq.com' }
        });

        inputElem.find('[test-data="code"]').simulate('change', {
            target: { value: '12TS8' }
        });


        const ButtonElem = wrapper.find("Button");
        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(mockFetchCheckCode).toBeCalled();
        expect(mockFetchUserPasswordAndEmail).not.toBeCalled();

        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(mockFetchUserPasswordAndEmail).toBeCalled();
        expect(fn1).toBeCalled();

        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn2).toBeCalled();

        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn3).toBeCalled();

        ButtonElem.find('[test-data="confirm"]').simulate('click');
        expect(fn4).toBeCalled();
    })
})