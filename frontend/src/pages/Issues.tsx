import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AppContext } from '../main';
import useAuth from '../hooks/useAuth';
import Header from '../components/landmark/Header';
import IssueTable from '../components/issues/IssueTable';
import Main from '../components/landmark/Main';
import { Link } from 'react-router-dom';
import ContextLogo from '../types/ContextLogo';
import FilterBar from '../components/common/FilterBar';
import Toolbar from '../components/landmark/Toolbar';
import { styled } from 'styled-components';
import TabButton from '../components/common/TabButton';
import Layout from '../components/Layout';
import Button from '../components/common/button/BaseButton';
import { Data } from '../types';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import DropdownPanel from '../components/common/DropdownPanel';

enum Option {
  Available,
  Selected,
}

export default function Issues() {
  const { util } = useContext(AppContext);
  const { auth, logout } = useAuth();
  const logo = (util.getLogoByTheme() as ContextLogo).medium;
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();
  const [filterValue, setFilterValue] = useState<string>('status:open');

  const [data, setData] = useState<Data>({
    openCount: 1,
    closedCount: 1,
    labelCount: 1,
    milestoneCount: 1,
    issues: [
      {
        id: 1,
        title: '제목',
        createdAt: '2023-07-24 17:22',
        user: {
          name: '감귤',
          profileImg: 'ddd.png',
        },
        labels: [
          {
            id: 1,
            name: 'documentation',
            backgroundColor: '#0025E6',
            textColor: '#0025E6',
          },
          {
            id: 2,
            name: 'documentation',
            backgroundColor: '#0025E6',
            textColor: '#0025E6',
          },
        ],
        milestone: {
          id: 1,
          name: 'sprint1',
        },
      },
      {
        id: 2,
        title: '제목2',
        createdAt: '2023-07-24 17:24',
        user: {
          name: '감귤',
          profileImg: 'ddd.png',
        },
        labels: [
          {
            id: 3,
            name: 'fix',
            backgroundColor: '#0025E6',
            textColor: '#0025E6',
          },
        ],
        milestone: {
          id: 2,
          name: 'sprint2',
        },
      },
    ],
  });

  // useEffect(() => {
  //   const fetchData = async () => {
  //     const params = {
  //       status: 'open',
  //       labels: '',
  //       milestone: '',
  //       writer: '',
  //       assignees: '',
  //       comment: '',
  //     };

  //     const res = await axiosPrivate.get('/api/issues', { params });

  //     try {
  //       if (res.status === 200) {
  //         setData(res.data.message);
  //       }
  //     } catch (error) {
  //       console.error(error);
  //     }
  //   };
  //   fetchData();
  // }, []);
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
        <Button type="button" ghost onClick={handleLogout}>
          로그아웃
        </Button>
        <div>
          {/* profile */}
          popopo
        </div>
      </Header>
      <Toolbar>
        <FilterBar
          filterValue="hi"
          openPanel={() => {
            console.log('open filterbar');
          }}
        />
        <FilterBarPanel
          elements={[
            ['열린 이슈', Option.Selected],
            ['내가 작성한 이슈', Option.Available],
            ['나에게 할당된 이슈', Option.Available],
            ['내가 댓글을 남긴 이슈', Option.Available],
            ['닫힌 이슈', Option.Available],
          ]}
          label="이슈 필터"
        />
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
          <Button type="button" iconName="plus">
            이슈 작성
          </Button>
        </ActionGroup>
      </Toolbar>
      <Main>
        {data ? <IssueTable issues={data.issues} /> : <p>서버오류</p>}
      </Main>
      {/* <button onClick={() => control.logoutCheck()}>logout</button> */}
    </Layout>
  );
}

const ActionGroup = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;

const FilterBarPanel = styled(DropdownPanel)`
  position: absolute;
  z-index: 1;
  top: 50px;
  background-color: ${({ theme }) => theme.color.neutral.surface.strong};
`;
