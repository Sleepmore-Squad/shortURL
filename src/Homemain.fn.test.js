import Homemain from "./components/Homemain";
import Fetch from "./fetch";

jest.mock("./fetch");
const mockFetchUserDailyCount = jest.fn().mockReturnValueOnce(3).mockReturnValueOnce(10);
const mockFetchLatestUrlId = jest.fn().mockReturnValue(12);
const mockFetchInsertUrl = jest.fn();

describe("test some methods in Homemain.js", () =>
{
  beforeAll(() =>
  {
    Fetch.mockImplementation(() => ({
      fetchUserDailyCount: mockFetchUserDailyCount,
      fetchLatestUrlId: mockFetchLatestUrlId,
      fetchInsertUrl: mockFetchInsertUrl
    }))
  });

  beforeEach(() =>
  {
    mockFetchUserDailyCount.mockClear();
    mockFetchLatestUrlId.mockClear();
    mockFetchInsertUrl.mockClear();
    Fetch.mockClear();
  })

  it("test method handleCount()", () =>
  {
    const homemain = new Homemain();
    expect(Fetch).toHaveBeenCalled();//检测构造函数是否被调用

    homemain.handleCount();
    expect(mockFetchUserDailyCount).toBeCalled();
    expect(mockFetchLatestUrlId).toBeCalled();

    homemain.handleCount();
    expect(mockFetchLatestUrlId).toBeCalledTimes(1);
  });

  it("test method handleInsert()", () =>
  {
    const homemain = new Homemain();
    expect(Fetch).toBeCalledTimes(1);//检测mockClear

    homemain.handleInsert();
    expect(mockFetchLatestUrlId).toBeCalled();
    expect(mockFetchInsertUrl).toBeCalled();
  });

  it("test method handleSendtoback(urlid, userId)", () =>
  {
    const homemain = new Homemain();
    expect(Fetch).toBeCalledTimes(1);//检测mockClear

    homemain.handleSendtoback(1, 1);
    expect(mockFetchInsertUrl).toBeCalled();
  })
})

