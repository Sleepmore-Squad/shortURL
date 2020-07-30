import React from 'react';
import { Layout, Menu } from 'antd';
import { withRouter } from 'react-router-dom';
const { Sider } = Layout;

class Sidermain extends React.Component {

    static defaultProps = {
        home: () => { },
        set: () => { },
    };

    constructor(props) {
        super(props);
        this.handleHome = this.handleHome.bind(this);
        this.handleSet = this.handleSet.bind(this);
    }

    handleHome = () => {

        this.props.history.push({ pathname: '/home' });
        this.props.home();
    };

    handleSet = () => {

        this.props.history.replace({pathname: '/Setting'});
        this.props.set();
    }

    render() {
        return (
            <Sider className="site-layout-background" width={200}>
                <Menu
                    mode="inline"
                    defaultSelectedKeys={['1']}
                    defaultOpenKeys={['sub1']}
                    style={{ height: '100%' }}
                    className="menu"
                >
                    <Menu.Item className="Home" key="setting:1" text-align="center" onClick={this.handleHome}>Home</Menu.Item>
                    <Menu.Item className="Set" key="setting:2" text-align="center" onClick={this.handleSet}>Set</Menu.Item>

                </Menu>
            </Sider>
        );
    }
}

export default withRouter(Sidermain) ;