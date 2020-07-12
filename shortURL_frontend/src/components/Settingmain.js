import React from 'react';
import { Button} from 'antd';


class Settingmain extends React.Component{
    constructor(props) {
        super(props);
        this.state={
            oriurl:'',
            shorturl:'',
        };
        this.handleCreate=this.handleCreate.bind(this)
    }

    handleCreate(e){

    }



    render(){
     return(
         <div >

             <Button onClick={this.handleCreate}>删除</Button>
         </div>
     )
    }
}

export default Settingmain ;