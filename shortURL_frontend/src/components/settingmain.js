import React from 'react';
import {Table,Button,Input,DatePicker,Popconfirm} from 'antd';
import { SearchOutlined } from '@ant-design/icons';
import '../css/setting.css'

const {RangePicker} = DatePicker;

class settingmain extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            searchText:'',  //username
            searchedColumn: '',
            data:[],
            begin_time:'',
            end_time:'',
        };


        this.getData=this.getData.bind(this);
        this.handleDelete=this.handleDelete.bind(this);
    }

    timeChange = (data) =>{
        if (data!==null)
            this.setState({
                begin_time:data[0].format('YYYY-MM-DD'),
                end_time:data[1].format('YYYY-MM-DD'),
            });
        else
            this.setState({
                begin_time:'',
                end_time:'',
            });
    };

    // componentDidMount=()=> {
    //     this.getData();
    // };

    //根据日期筛选数据
    getData = () => {
        if(this.state.begin_time!==''&& this.state.end_time !=='') {
            let formdata = new FormData();
            formdata.append('begin_time', this.state.begin_time);
            formdata.append('end_time', this.state.end_time);

            let opts = {
                method: "POST",
                body: formdata,
                headers: {
                    "Authorization": sessionStorage.getItem('token')
                }
            };

            fetch('http://localhost:8181/order/getDataByTime', opts)
                .then((response) => {
                    return response.json()
                })
                .then((data) => {
                    this.setState({data: data});
                    console.log(data);
                    console.log(this.state.data[0].urlinfo.oriURL);
                });
        }
    };

    //选择特定用户
    getColumnSearchProps = dataIndex => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
            <div style={{ padding: 8 }}>
                <Input
                    ref={node => {
                        this.searchInput = node;
                    }}
                    placeholder={`Search ${dataIndex}`}
                    value={selectedKeys[0]}
                    onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                    onPressEnter={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
                    style={{ width: 188, marginBottom: 8, display: 'block' }}
                />

                <Button
                    type="primary"
                    onClick={() => this.handleSearch(selectedKeys, confirm, dataIndex)}
                    icon={<SearchOutlined />}
                    size="small"
                    style={{ width: 90 }}
                >
                    Search
                </Button>
                <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
                    Reset
                </Button>

            </div>
        ),
        filterIcon: filtered => <SearchOutlined style={{ color: filtered ? '#1890ff' : undefined }} />,
        onFilter: (value, record) =>
            record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: visible => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: text => text,
    });

    handleSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        this.setState({
            searchText: selectedKeys[0],
            searchedColumn: dataIndex,
        });
    };

    handleReset = clearFilters => {
        clearFilters();
        this.setState({ searchText: '' });
    };

    handleDelete(urlId,key){

        const data = [...this.state.data];
        this.setState({ data: data.filter(item => item.key !== key) });
        // const data1 = this.state.data;
        // const newData = data1;
        // const index = newData.findIndex((item) => key === item.key);
        // newData.splice(index, 1);
        // this.setState({ data: newData });
        let formdata = new FormData();
        formdata.append('url_id',urlId);
        let opts =
            {   method: "POST",
                body: formdata,
                headers: {
                    "Authorization": sessionStorage.getItem('token')
                }
            };

        fetch('http://localhost:8181/url/block',opts)
            .then(() => {

            });

    }


    render() {
        const columns = [
            {
                title: '用户名',
                dataIndex: 'userId',
                key:'username',
                ...this.getColumnSearchProps('username'),
            },
            {
                title: '原网址',
                dataIndex: ['urlinfo','oriURL'],
                key:'oriURL',
            },
            {
                title: '短链接',
                dataIndex: ['urlinfo','shortURL'],
                key:'shortURL',
            },
            {
                title: 'Blocked?',
                dataIndex: ['urlinfo','block'],
                key:'block',
            },
            {
                title: '操作',
                dataIndex: 'operation',
                render: (text, record) =>
                    this.state.data.length >= 1 ? (
                        <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record.urlId,record.key)}>
                            <a href="">Delete</a>
                        </Popconfirm>
                    ) : null,
            },
        ];

        return (
            <div>
                <RangePicker format="YYYY-MM-DD" onChange={this.timeChange} style={{marginLeft:"10%"}}/>
                <Button type="primary" shape="round" style={{float:"right",marginRight:"20%"}} onClick={this.getData}>
                    Get Data
                </Button>
                <Table columns={columns} dataSource={this.state.data} />
            </div>
        );
    }
}

export default settingmain;