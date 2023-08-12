import { Theme, css, useTheme } from '@emotion/react';
import SubNavBar from '../SubNavbar';
import TableContainer from '../TableContainer';
import { border, font, radius } from '../../styles/styles';
import LabelItem from './LabelItem';
import { useEffect, useState } from 'react';
import { customFetch } from '../../util/customFetch';
import { useNavigate } from 'react-router-dom';

export default function LabelList() {
  const theme = useTheme();
  const [labelList, setLabelList] = useState<LabelData>();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      const subUrl = 'api/labels';

      try {
        const labelData = await customFetch<LabelResponse>({ subUrl });

        if (labelData.success && labelData.data) {
          setLabelList(labelData.data);
        }
      } catch (error) {
        navigate('/sign-in');
      }
    })();
  }, []);

  const onClickToCreate = () => {};

  return (
    <>
      {labelList && (
        <>
          <SubNavBar
            isIssue={false}
            labelCount={labelList.labels.length}
            milestoneCount={labelList.milestoneCount}
            buttonValue="레이블 추가"
            onClick={onClickToCreate}
          />
          <TableContainer>
            <div css={labelTable(theme)}>
              <div className="header">{labelList.labels.length}개의 레이블</div>
              <ul className="item-container">
                {labelList.labels.map((label) => (
                  <LabelItem key={label.id} {...label} />
                ))}
              </ul>
            </div>
          </TableContainer>
        </>
      )}
    </>
  );
}

const labelTable = (theme: Theme) => css`
  display: flex;
  flex-direction: column;
  border-radius: ${radius.medium};
  color: ${theme.neutral.textDefault};

  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 64px;
    padding: 0 32px;
    border-radius: ${radius.medium} ${radius.medium} 0 0;
    border: ${border.default} ${theme.neutral.borderDefault};
    background-color: ${theme.neutral.surfaceDefault};
    font: ${font.displayBold16};
    color: ${theme.neutral.textDefault};
  }

  .item-container {
    display: flex;
    flex-direction: column;
    border: ${border.default} ${theme.neutral.borderDefault};
    border-top: none;
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
