import axios from './axios';
import { IssueInfo } from '../pages/AddIssue';
import { AxiosError } from 'axios';

export const postNewIssue = async (issueInfo: IssueInfo) => {
  try {
    const res = await axios.post('/api/issues', issueInfo, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (err) {
    if (err instanceof AxiosError) {
      alert('에러가 발생했습니다.\n' + '실패 사유: ' + err.message);
      console.error(err);
    }
  }
};
