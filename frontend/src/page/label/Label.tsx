import { useEffect, useState } from "react";
import { styled } from "styled-components";
import { getAccessToken } from "../../utils/localStorage";
import { LabelEditor } from "./LabelEditor";
import { LabelHeader } from "./LabelHeader";
import { LabelTable } from "./LabelTable";

export type LabelData = {
  id: number;
  name: string;
  color: "LIGHT" | "DARK";
  background: string;
  description: string | null;
};

type LabelsResData = {
  openedMilestoneCount: number;
  labelCount: number;
  labels: LabelData[];
};

export function Label() {
  const [labelsRes, setLabelsRes] = useState<LabelsResData>();
  const [isAdding, setIsAdding] = useState(false);

  const openAddLabel = () => {
    setIsAdding(true);
  };

  const closeAddLabel = () => {
    setIsAdding(false);
  };

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    const res = await fetch("/api/labels", {
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getAccessToken()}`,
      },
    });
    const labelsData = await res.json();

    if (labelsData.code === 200) {
      setLabelsRes(labelsData.data);
    }
    // TODO : 에러 예외 처리
  };

  return (
    <Div>
      {labelsRes && (
        <LabelHeader
          onClick={openAddLabel}
          openedMilestoneCount={labelsRes.openedMilestoneCount}
          labelCount={labelsRes.labelCount}
          isAdding={isAdding}
        />
      )}
      {isAdding && (
        <EditorWrapper>
          <LabelEditor
            fetchData={fetchData}
            onClickClose={closeAddLabel}
            type="add"
          />
        </EditorWrapper>
      )}
      {labelsRes && (
        <LabelTable fetchData={fetchData} labels={labelsRes.labels} />
      )}
    </Div>
  );
}

const Div = styled.div`
  width: 1280px;
  display: flex;
  flex-direction: column;
`;

const EditorWrapper = styled.div`
  border: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background: ${({ theme }) => theme.color.neutralSurfaceStrong};
  margin-bottom: 24px;
  padding: 32px;
`;
