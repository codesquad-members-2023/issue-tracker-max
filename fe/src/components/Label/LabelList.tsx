import { Theme, css, useTheme } from '@emotion/react';
import SubNavBar from '../SubNavbar';
import TableContainer from '../TableContainer';
import { border, font, radius } from '../../styles/styles';
import LabelItem from './LabelItem';

const mockData = {
  success: 'true',
  data: {
    milestoneCount: 2,
    labels: [
      {
        id: 1,
        name: 'Backend',
        description: '백엔드',
        textColor: '#6E7191',
        backgroundColor: '#FEFEFE',
      },
      {
        id: 2,
        name: 'Frontend',
        description: '프론트엔드',
        textColor: '#FEFEFE',
        backgroundColor: '#0025E6',
      },
    ],
  },
};

export default function LabelList() {
  const theme = useTheme();

  return (
    <>
      <SubNavBar
        isIssue={false}
        labelCount={mockData.data.labels.length}
        milestoneCount={mockData.data.milestoneCount}
        buttonValue="레이블 추가"
      />
      <TableContainer>
        <div css={labelTable(theme)}>
          <div className="header">{mockData.data.labels.length}개의 레이블</div>
          <ul className="item-container">
            {mockData.data.labels.map((label) => (
              <LabelItem key={label.id} {...label} />
            ))}
          </ul>
        </div>
      </TableContainer>
    </>
  );
}

const labelTable = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  border-radius: ${radius.medium};
  border: ${border.default} ${theme.neutral.borderDefault};
  color: ${theme.neutral.textDefault};

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 64px;
    padding: 0 32px;
    border-radius: ${radius.medium} ${radius.medium} 0 0;
    border-bottom: ${border.default} ${theme.neutral.borderDefault};
    background-color: ${theme.neutral.surfaceDefault};
    font: ${font.displayBold16};
    color: ${theme.neutral.textDefault};
  }

  .item-container {
    display: flex;
    flex-direction: column;
    border-radius: 0 0 ${radius.medium} ${radius.medium};
    background-color: ${theme.neutral.surfaceStrong};

    li {
      height: 96px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      box-sizing: border-box;
      padding: 0 32px;
      border-bottom: ${border.default} ${theme.neutral.borderDefault};

      &:last-child {
        border-bottom: none;
        border-radius: 0 0 ${radius.medium} ${radius.medium};
      }
    }
  }
`;
