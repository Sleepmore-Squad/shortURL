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
    };
    const response = await fetch('http://localhost:8181/order/count/' + userId + '/' + date, opts);
    const data = await response.json();
    return data;
  };

  fetchLatestUrlId = async () =>
  {
    const response = await fetch("http://localhost:8181/url/getCount", { "method": "GET" });
    const data = await response.json();
    return data;
  };

  fetchInsertUrl = (opts) =>
  {
    fetch("http://localhost:8181/url/insert", opts);
  };

  fetchSubmitData = async (opts) =>
  {
    const response = await fetch('http://localhost:8181/user/login', opts);
    const data = response.json();
    return data;
  };

  fetchEmailSend = (opts) =>
  {
    fetch('http://localhost:8181/user/register/send', opts);
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