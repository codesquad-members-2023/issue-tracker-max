import { useContext } from 'react';
import { AppContext } from '../main';
import Header from '../components/landmark/Header';
import IssueTable from '../components/issues/IssueTable';
import Main from '../components/landmark/Main';
import { Link, useNavigate } from 'react-router-dom';
import ContextLogo from '../types/ContextLogo';
import FilterBar from '../components/common/FilterBar';
import Toolbar from '../components/landmark/Toolbar';
import { styled } from 'styled-components';
import TabButton from '../components/common/TabButton';
import Layout from '../components/Layout';
import Button from '../components/common/button/BaseButton';

export default function Issues() {
  const { util } = useContext(AppContext);
  const logo = (util.getLogoByTheme() as ContextLogo).medium;
  const navigate = useNavigate();

  return (
    <Layout>
      <Header>
        <Link to="/">
          <img src={logo} alt="이슈트래커" />
        </Link>
        <div>
          {/* profile */}
          popopo
        </div>
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
        <IssueTable />
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
