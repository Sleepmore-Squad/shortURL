import Fetch from "../fetch";
import fetchMock from "fetch-mock-jest";

let d = new Date();
let year = d.getFullYear();
let month = d.getMonth() + 1;
let day = d.getDate();
let date = year + '-' + month + '-' + day;

fetchMock
  .get('http://localhost:8181/order/count/1/' + date, "202")
  .get("http://localhost:8181/url/getCount", "1")
  .post("http://localhost:8181/url/insert", "200")
  .post('http://localhost:8181/user/login', "200")
  .post('http://localhost:8181/user/register/send', "200")
  .post('http://localhost:8181/user/register/check', "200")
  .post('http://localhost:8181/user/register', "200")

const fetch = new Fetch();
describe("test fetch.js", () =>
{
  it("test fetchUserDailyCount()", async () => 
  {
    const data = await fetch.fetchUserDailyCount(1);
    expect(data).toEqual(202);
  })

  it("test fetchLatestUrlId()", async () =>
  {
    const data = await fetch.fetchLatestUrlId();
    expect(data).toEqual(1);
  })

  it("test fetchInsertUrl()", () => 
  {
    fetch.fetchInsertUrl({ "method": "POST" });
    expect(fetchMock).toBeCalledWith("http://localhost:8181/url/insert", { "method": "POST" });
  })

  it("test fetchSubmitData()", async () => 
  {
    const data = await fetch.fetchSubmitData({ "method": "POST" });
    expect(data).toEqual(200);
  })

  it("test fetchEmailSend()", async () => 
  {
    const data = await fetch.fetchEmailSend({ "method": "POST" });
    expect(data).toEqual(200);
  })

  it("test fetchCheckCode()", async () => 
  {
    const data = await fetch.fetchCheckCode({ "method": "POST" });
    expect(data).toEqual(200);
  })

  it("test fetchUserPasswordAndEmail()", async () => 
  {
    const data = await fetch.fetchUserPasswordAndEmail({ "method": "POST" });
    expect(data).toEqual(200);
  })
})