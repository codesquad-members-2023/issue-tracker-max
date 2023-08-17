import React, { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AppContext } from '../main';
import useAuth from '../hooks/useAuth';
import Header from '../components/landmark/Header';
import IssueTable from '../components/issues/IssueTable';
import Main from '../components/landmark/Main';
import ContextLogo from '../types/ContextLogo';
import Toolbar from '../components/landmark/Toolbar';
import { styled } from 'styled-components';
import TabButton from '../components/common/TabButton';
import FilterBar from '../components/common/FilterBar';
import Layout from '../components/Layout';
import Button from '../components/common/button/BaseButton';
import { Data } from '../types';
import axios from 'axios';
import useAxiosPrivate from '../hooks/useAxiosPrivate';

export default function Issues() {
  const { util } = useContext(AppContext);
  const { auth, logout } = useAuth();
  const logo = (util.getLogoByTheme() as ContextLogo).medium;
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const [data, setData] = useState<Data>(null);

  util.getFilter = () => (keywords: string) => {
    const fetchData = async () => {
      const params = keywordParser(keywords);

      const res = await axios.get('/api/issues', {
        params,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
        },
      });

      try {
        if (res.status === 200) {
          setData(res.data.message);
          console.log(res.data.message);
        }
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  };

  const handleLogout = async () => {
    if (!auth) {
      return;
    }

    try {
      const res = await axiosPrivate.post('/api/logout', null, {
        headers: { 'Content-Type': 'application/json' },
        withCredentials: true,
      });

      if (res.status === 200) {
        localStorage.clear();
        logout();
        navigate('/login');
      } else {
        console.error('로그아웃 실패:', res.status);
      }
    } catch (err) {
      console.error('로그아웃 에러:', err);
    }
  };

  return (
    <Layout>
      <Header>
        <Link to="/">
          <img src={logo} alt="이슈트래커" />
        </Link>
        <UserInfo>
          {/* profile */}
          popopo
          <Button type="button" ghost flexible onClick={handleLogout}>
            로그아웃
          </Button>
        </UserInfo>
      </Header>
      <Toolbar>
        <FilterBar />
        <ActionGroup>
          <TabButton
            tabs={[
              {
                iconName: 'label',
                text: '레이블',
                event: () => {},
              },
              {
                iconName: 'milestone',
                text: '마일스톤',
                event: () => {},
              },
            ]}
          />
          <Button
            type="button"
            iconName="plus"
            onClick={() => {
              navigate('/addIssue');
            }}>
            이슈 작성
          </Button>
        </ActionGroup>
      </Toolbar>
      <Main>
        {data ? <IssueTable issues={data.issueReads} /> : <p>서버오류</p>}
      </Main>
      {/* <button onClick={() => control.logoutCheck()}>logout</button> */}
    </Layout>
  );
}

const UserInfo = styled.div`
  display: flex;
  gap: 24px;
  align-items: center;
  & > button {
    color: ${({ theme }) => theme.color.neutral.surface.strong};
    background-color: ${({ theme }) => theme.color.danger.text.default};
    padding: 8px 24px;
    border-radius: ${({ theme }) => theme.objectStyles.radius.large};
  }
`;

const ActionGroup = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;

function keywordParser(keyword: string) {
  const params: Record<string, string> = {};
  keyword.split(' ').forEach((token) => {
    const [key, value] = token.split(':');
    params[key] = value;
  });
  return params;
}
