import { css } from '@emotion/react';
import { customFetch } from '../util/customFetch';
import {
  GetAssigneesRes,
  GetLabelsRes,
  GetMilestonesRes,
  GetWritersRes,
  OnlySuccessRes,
} from '../type/Response.type';

export default function ApiTest() {
  const getAssignees = async () => {
    const response = await customFetch<GetAssigneesRes>({
      subUrl: 'api/members/show-content',
    });

    console.log(response.success);
  };

  const getLabels = async () => {
    const response = await customFetch<GetLabelsRes>({
      subUrl: 'api/labels/show-content',
    });

    console.log(response.success);
  };

  const getMilestones = async () => {
    const response = await customFetch<GetMilestonesRes>({
      subUrl: 'api/milestones/show-content',
    });

    console.log(response.success);
  };

  const getWriters = async () => {
    const response = await customFetch<GetWritersRes>({
      subUrl: 'api/members/show-content',
    });

    console.log(response.success);
  };

  const patchAssignees = async () => {
    const issueId = 6;
    const assigneeId = 1;
    const response = await customFetch<OnlySuccessRes>({
      method: 'PATCH',
      subUrl: `api/issues/${issueId}/assignees`,
      body: JSON.stringify({
        ids: [assigneeId],
      }),
    });

    console.log(response.success);
  };

  const patchLabels = async () => {
    const issueId = 6;
    const labelId = 1;
    const response = await customFetch<OnlySuccessRes>({
      method: 'PATCH',
      subUrl: `api/issues/${issueId}/labels`,
      body: JSON.stringify({
        ids: [labelId],
      }),
    });

    console.log(response.success);
  };

  const patchMilestones = async () => {
    const issueId = 6;
    const milestoneId = 1;
    const response = await customFetch<OnlySuccessRes>({
      method: 'PATCH',
      subUrl: `api/issues/${issueId}/milestones/${milestoneId}`,
    });

    console.log(response.success);
  };

  return (
    <div css={test}>
      <button type="button" onClick={getAssignees}>
        GET 담당자를 불러온다
      </button>
      <button type="button" onClick={getLabels}>
        GET 레이블을 불러온다
      </button>
      <button type="button" onClick={getMilestones}>
        GET 마일스톤을 불러온다
      </button>
      <button type="button" onClick={getWriters}>
        GET 작성자를 불러온다
      </button>
      <button type="button" onClick={patchAssignees}>
        PATCH 이슈의 담당자를 수정한다
      </button>
      <button type="button" onClick={patchLabels}>
        PATCH 이슈 레이블을 수정한다
      </button>
      <button type="button" onClick={patchMilestones}>
        PATCH 이슈 마일스톤을 수정한다
      </button>
      {/* <button type="button" onClick={deleteComments}>
        DELETE 이슈 상세페이지의 코멘트를 삭제한다
      </button> */}
    </div>
  );
}

const test = css`
  display: flex;
  flex-direction: column;
  gap: 10px;

  > button {
    width: 200px;
    height: 40px;
  }
`;
