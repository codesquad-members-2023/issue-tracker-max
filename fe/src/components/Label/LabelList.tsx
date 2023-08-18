import { Theme, css, useTheme } from '@emotion/react';
import SubNavBar from '../SubNavbar';
import TableContainer from '../TableContainer';
import { border, font, radius } from '../../styles/styles';
import LabelItem from './LabelItem';
import { useEffect, useState } from 'react';
import { customFetch } from '../../util/customFetch';
import { useNavigate } from 'react-router-dom';
import { LabelData, LabelType } from '../../type/label.type';
import { GetLabelRes } from '../../type/Response.type';
import LabelCreate from './LabelCreate/LabelCreate';
import { LabelProvider } from '../Context/LabelContext';

export default function LabelList() {
  const theme = useTheme();
  const navigate = useNavigate();
  const [isCreating, setIsCreating] = useState<boolean>(false);
  const [labelList, setLabelList] = useState<LabelData>();

  useEffect(() => {
    (async () => {
      try {
        const response = await customFetch<GetLabelRes>({
          subUrl: 'api/labels',
        });

        if (!response.success) {
          if (response.errorCode?.status === 401) {
            navigate('/sign-in');
            return;
          }
        }

        if (response.data) {
          setLabelList(response.data);
        }
      } catch (error) {
        navigate('/sign-in');
      }
    })();
  }, []);

  const addNewLabel = (newLabel: LabelType) => {
    setLabelList((prev) => {
      if (prev) {
        return { ...prev, labels: [...prev.labels, { ...newLabel }] };
      }
    });
  };

  const editLabel = (labelId: number, editedLabel: LabelType) => {
    setLabelList((prev) => {
      if (prev) {
        return {
          ...prev,
          labels: prev.labels.map((label) => {
            if (label.id === labelId) {
              return { ...editedLabel };
            }
            return label;
          }),
        };
      }
    });
  };

  const deleteLabel = (labelId: number) => {
    setLabelList((prev) => {
      if (prev) {
        return {
          ...prev,
          labels: prev.labels.filter((label) => label.id !== labelId),
        };
      }
    });
  };

  const onChangeStatus = () => {
    setIsCreating((prev) => !prev);
  };

  return (
    <LabelProvider>
      {labelList && (
        <>
          <SubNavBar
            isIssue={false}
            labelCount={labelList.labels.length}
            milestoneCount={labelList.milestoneCount}
            buttonValue="레이블 추가"
            onClick={onChangeStatus}
          />
          {isCreating && (
            <LabelCreate
              onChangeStatus={onChangeStatus}
              onAddNewLabel={addNewLabel}
            />
          )}
          <TableContainer>
            <div css={labelTable(theme)}>
              <div className="header">{labelList.labels.length}개의 레이블</div>
              <ul className="item-container">
                {labelList.labels.map((label) => (
                  <LabelItem
                    key={label.id}
                    label={label}
                    onDelete={deleteLabel}
                    onEdit={editLabel}
                  />
                ))}
              </ul>
            </div>
          </TableContainer>
        </>
      )}
    </LabelProvider>
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
    overflow: hidden;

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
