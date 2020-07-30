export class Fetch
{
  // eslint-disable-next-line no-useless-constructor
  constructor() { };

  fetchUserDailyCount = async (userId) =>
  {
    let d = new Date();
    let year = d.getFullYear();
    let month = d.getMonth() + 1;
    let day = d.getDate();
    let date = year + '-' + month + '-' + day;
    let opts = {
      method: "GET",
      headers: {
        "Authorization": sessionStorage.getItem('token')
      }
    };
    const response = await fetch('http://localhost:8181/order/count/' + userId + '/' + date, opts);
    const data = await response.json();
    console.log(data);
    return data;
  };

  fetchLatestUrlId = async () =>
  {
    let opts = {
      method: "GET",
      headers: {
        "Authorization": sessionStorage.getItem('token')
      }
    };
    const response = await fetch("http://localhost:8181/url/getCount",opts);
    const data = await response.json();
    console.log(data);
    return data;
  };

  fetchInsertUrl = (opts) =>
  {
    fetch("http://localhost:8181/url/insert", opts);
    // const response = await fetch("http://localhost:8181/url/insert", opts);
    // const data = response.json();
    // console.log(data);
    // return data;
  };

  fetchSubmitData = async (opts) =>
  {
    const response = await fetch('http://localhost:8181/user/login', opts);
    const data = await response.json();
    console.log(data);
    return data;
  };

  fetchEmailSend = async (opts) =>
  {
    const response = await fetch('http://localhost/user/register/send', opts);
    const data = response.json();
    return data;
  };

  fetchCheckCode = async (opts) =>
  {
    const response = await fetch('http://localhost:8181/user/register/check', opts);
    const data = response.json();
    return data;
  };

  fetchUserPasswordAndEmail = async (opts) => 
  {
    const response = await fetch('http://localhost:8181/user/register', opts);
    const data = response.json();
    return data;
  };
}

export default Fetch;